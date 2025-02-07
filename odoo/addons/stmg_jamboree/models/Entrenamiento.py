from odoo import models, api, fields

class Entrenamiento(models.Model):
    _name = "stmg_jamboree.entrenamiento"
    _description = 'Entrenamiento'
    
    name = fields.Char('name')
    #empleado_ids = fields.Many2many('comodel_name', string='empleado')
    #jugador_ids = fields.Many2many('comodel_name', string='empleado')
    tipo = fields.Char('tipo')
    turno = fields.Datetime('turno')