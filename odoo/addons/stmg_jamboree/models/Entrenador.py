from odoo import models, api, fields

class Entrenador(models.Model):
    _name = 'stmg_jamboree.entrenador'
    _inherit = 'res.partner'

    entrenamiento_ids = fields.Many2many('stmg_jamboree.entrenamiento', string='Entrenamientos', relation='stmg_jamboree_entrenamiento_entrenador_rel')