from odoo import models, fields

class Entrenador(models.Model):
    _name = 'stmg_jamboree.entrenador'
    _description = 'Entrenador'

    partner_id = fields.Many2one(
        'res.partner',
        string='Contacto',
        domain=[('is_entrenador', '=', True)],
        required=True,
    )

    cod = fields.Char(related='partner_id.id_entrenador', string='ID Entrenador', readonly=True, )
    name = fields.Char(related='partner_id.name', string='Nombre', readonly=True)
    email = fields.Char(related='partner_id.email', string='Email', readonly=True)
    telefono = fields.Char(related='partner_id.phone', string='Teléfono', readonly=True)
    fotografia = fields.Image(related='partner_id.image_1920', string='Fotografía', readonly=True)
    color = fields.Integer(related='partner_id.color', string='Color', readonly=True)
    user_id = fields.Many2one(related='partner_id.user_id', string='Usuario', readonly=True)

    entrenamiento_ids = fields.Many2many(
        'stmg_jamboree.entrenamiento',
        string='Entrenamientos',
        relation='stmg_jamboree_entrenamiento_entrenador_rel'
    )
