from odoo import models, fields, api

class Tutor(models.Model):
    _name = 'stmg_jamboree.tutor'
    _description = 'Tutor'
    
    name = fields.Char('id')
    nombre = fields.Char('nombre')
    apellidos = fields.Char('apellidos')
    email = fields.Char('email')
    telefono = fields.Integer('telefono')
    dni = fields.Char('dni')
    # jugador_ids = fields.Many2many('stmg_jamboree.Jugador', string='jugador')