from odoo import models, api, fields

class Sede(models.Model):
    _name = 'stmg_jamboree.sede'
    _description = 'Sede'
    
    name = fields.Char('id')
    nombre = fields.Char('nombre')
    #ciudad
    entrenamiento_ids = fields.One2many('stmg_jamboree.entrenamiento', 'sede_id', string='Entrenamientos')