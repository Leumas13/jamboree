from odoo import models, fields, api

class Tutor(models.Model):
    _name = 'stmg_jamboree.tutor'
    _inherit = 'res.partner'
    _description = 'Tutor'
    
    
    jugador_ids = fields.Many2many('stmg_jamboree.jugador', string='Jugador',relation='stmg_jamboree_tutor_jugador_rel')