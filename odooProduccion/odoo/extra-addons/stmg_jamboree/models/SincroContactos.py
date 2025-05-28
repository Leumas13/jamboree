from odoo import _, models, fields, api
from odoo.exceptions import ValidationError

class StmgSincroContactos(models.Model):
    _inherit = 'res.partner'

    is_tutor = fields.Boolean(string='Es Tutor', tracking=True)
    is_entrenador = fields.Boolean(string='Es Entrenador', tracking=True)
    id_tutor = fields.Char('ID Tutor', readonly=True, store=True)
    id_entrenador = fields.Char('ID Entrenador', readonly=True, store=True)
    color = fields.Integer(string='Color', readonly=True, store=True)
    jugador_ids = fields.Many2many('stmg_jamboree.jugador', string='Jugadores', tracking=True)
    entrenamiento_ids = fields.Many2many('stmg_jamboree.entrenamiento', string='Entrenamientos', tracking=True)

    user_id = fields.Many2one('res.users', string='Usuario relacionado')
    clave = fields.Char(string='Contraseña', help="Contraseña para el nuevo usuario")  # <- asegúrate de tener esto si usas 'record.clave'

    @api.onchange('is_entrenador')
    def _onchange_is_entrenador(self):
        if not self.is_entrenador:
            self.entrenamiento_ids = [(5, 0, 0)]

    @api.model_create_multi
    def create(self, values_list):
        records = super().create(values_list)
        for record in records:
            record.color = record.id % 11 + 1
            if record.is_entrenador and not record.id_entrenador:
                if not record.email:
                    raise ValidationError(_('El entrenador debe tener un correo electrónico para crear el usuario.'))
                record.id_entrenador = f'ENT_{record.id}'

                # Si ya existe un usuario, no lo creamos de nuevo
                if not record.user_id:
                    user = self.env['res.users'].sudo().create({
                        'name': record.name,
                        'login': record.email,
                        'email': record.email,
                        'partner_id': record.id,
                        'groups_id': [(6, 0, [
                                self.env.ref('base.group_portal').id,
                                self.env.ref('stmg_jamboree.stmg_group_entrenador').id,
                            ])],
                        'password': record.clave or 'entrenador123',  # contraseñas deben definirse correctamente
                    })
                    record.user_id = user
        return records

    def write(self, vals):
        res = super().write(vals)
        for record in self:
            if 'is_entrenador' in vals:
                if vals['is_entrenador']:
                    if not record.id_entrenador:
                        record.id_entrenador = f'ENT_{record.id}'

                    if not record.email:
                        raise ValidationError(_('El entrenador debe tener un correo electrónico.'))

                    # Si ya existe un usuario, no lo creamos de nuevo
                    if not record.user_id:
                        user = self.env['res.users'].sudo().create({
                            'name': record.name,
                            'login': record.email,
                            'email': record.email,
                            'partner_id': record.id,
                            'groups_id': [(6, 0, [
                                self.env.ref('base.group_portal').id,
                                self.env.ref('stmg_jamboree.stmg_group_entrenador').id,
                            ])],
                            'password': record.clave or 'entrenador123',  # contraseñas deben definirse correctamente
                        })
                        record.user_id = user
                else:
                    # Eliminar usuario relacionado si existe
                    if record.user_id:
                        record.user_id.sudo().unlink()
                        record.user_id = False
                    record.id_entrenador = None
                    record.entrenamiento_ids = [(5, 0, 0)]
        return res

    # Crear boton en la zona de arriba con entrenamientos, similar a grupos, reuniones, etc.
    def action_view_entrenamientos(self):
            self.ensure_one()
            action = self.env.ref('stmg_jamboree.action_entrenamiento_list').read()[0]
            action['domain'] = [('entrenador_ids', 'in', self.id)]
            action['context'] = {'default_entrenador_ids': [self.id]}
            return action