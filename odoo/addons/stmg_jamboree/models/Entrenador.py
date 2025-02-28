from odoo import models, api, fields

class Entrenador(models.Model):
    _name = 'stmg_jamboree.entrenador'
    _description = 'Entrenador'
    
    name = fields.Char('id')
    nombre = fields.Char('Nombre')
    apellidos = fields.Char('Apellidos')
    email = fields.Char('e-mail')
    telefono = fields.Integer('telefono')
    fotografia = fields.Image('Fotografia')
    entrenamiento_ids = fields.Many2many('stmg_jamboree.entrenamiento', string='Entrenamientos', relation='stmg_jamboree_entrenamiento_entrenador_rel')