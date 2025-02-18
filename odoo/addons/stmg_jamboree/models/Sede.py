from odoo import models, api, fields

class Sede(models.Model):
    _name = 'stmg_jamboree.sede'
    _description = 'Sede'
    
    name = fields.Char('id')
    nombre = fields.Char('Nombre')
    fotografia = fields.Binary('Fotografia')
    ciudad_id = fields.Many2one('res.city', string='Ciudad')
    entrenamiento_ids = fields.One2many('stmg_jamboree.entrenamiento', 'sede_id', string='Entrenamientos')