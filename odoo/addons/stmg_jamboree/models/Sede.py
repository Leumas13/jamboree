from odoo import models, api, fields

class Sede(models.Model):
    _name = 'stmg_jamboree.sede'
    _description = 'Sede'
    
    name = fields.Char('name')
    # entrenamiento = fields.One2many('comodel_name', 'inverse_field_name', string='# entrenamiento