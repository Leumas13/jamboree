from odoo import models, api, fields

class Entrenador(models.Model):
    _name = 'stmg_jamboree.entrenador'
    _description = 'Entrenador'
    
    name = fields.Char('id')
    nombre = fields.Char('nombre')
    apellidos = fields.Char('apellidos')
    email = fields.Char('email')
    telefono = fields.Integer('telefono')
    #Entrenamiento = fields.Many2many('#Sede string='field_name')