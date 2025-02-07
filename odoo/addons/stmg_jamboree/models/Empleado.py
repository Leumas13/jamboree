from odoo import models, api, fields

class Empleado(models.Model):
    _name = 'stmg_jamboree.empleado'
    _description = 'Empleado'
    
    name = fields.Char('name')
    nombre = fields.Char('nombre')
    Apellidos = fields.Char('Apellidos')
    email = fields.Char('email')
    telefono = fields.Integer('telefono')
    #Sede = fields.Many2many('#Sede string='field_name')
    #Entrenamiento = fields.Many2many('#Sede string='field_name')