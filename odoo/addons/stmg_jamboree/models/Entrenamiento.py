from odoo import models, api, fields

class Entrenamiento(models.Model):
    _name = "stmg_jamboree.entrenamiento"
    _description = 'Entrenamiento'
    
    name = fields.Char('id', required=True, index=True)
    tipo = fields.Selection([
        ('personal', 'Personal'),
        ('infantil', 'Infantil'),
        ('desarrollo', 'Desarrollo'),
    ], string='Tipo')
    turno = fields.Datetime('Turno')
    
    sede_id = fields.Many2one('stmg_jamboree.sede', string='Sede')
    nombre_sede = fields.Char(related='sede_id.nombre', string="Nombre de Sede")
    
    entrenador_ids = fields.Many2many('stmg_jamboree.entrenador', string='Entrenador', relation='stmg_jamboree_entrenamiento_entrenador_rel')
    jugador_ids = fields.Many2many('stmg_jamboree.jugador', string='Jugador', relation='stmg_jamboree_entrenamiento_jugador_rel')
