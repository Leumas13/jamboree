from odoo import models, fields, api

class Tutor(models.Model):
    _name = 'stmg_jamboree.tutor'
    _description = 'Tutor'
    _inherit = ['mail.thread', 'mail.activity.mixin']
    
    name = fields.Char('id', readonly=True)
    nombre = fields.Char('Nombre', tracking=True)
    apellidos = fields.Char('Apellidos', tracking=True)
    email = fields.Char('e-mail', tracking=True)
    telefono = fields.Char('Telefono', tracking=True)
    dni = fields.Char('DNI', tracking=True)
    clave = fields.Char('Clave')
    jugador_ids = fields.Many2many('stmg_jamboree.jugador', string='Jugador',relation='stmg_jamboree_tutor_jugador_rel', tracking=True)

    partner_id = fields.Many2one('res.partner', string='Contacto')
    user_id = fields.Many2one('res.users', string='Usuario')

    @api.model_create_multi
    def create(self, values_list):
        records = super().create(values_list)
        for record in records:
            # Nombre técnico y clave
            record.name = f'TUTOR_{record.id}'
            record.clave = f'TUTOR_{record.id}1234'

            # Validación
            if not record.email:
                raise ValidationError(_('El tutor debe tener un correo electrónico para crear el usuario.'))

            # Crear contacto (res.partner)
            partner = self.env['res.partner'].create({
                'name': f"{record.nombre or ''} {record.apellidos or ''}".strip(),
                'email': record.email,
                'phone': record.telefono,
                'is_company': False,
                'company_type': 'person',
            })
            record.partner_id = partner

            # Crear usuario (res.users)
            user = self.env['res.users'].sudo().create({
                'name': partner.name,
                'login': record.email,
                'email': record.email,
                'partner_id': partner.id,
                'groups_id': [(6, 0, [self.env.ref('base.group_portal').id])],
                'password': record.clave,
            })
            record.user_id = user

        return records