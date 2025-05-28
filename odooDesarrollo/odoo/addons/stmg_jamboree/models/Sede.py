from odoo import models, api, fields
from datetime import datetime

class Sede(models.Model):
    _name = 'stmg_jamboree.sede'
    _description = 'Sede'
    _inherit = ['mail.thread', 'mail.activity.mixin']
    
    cod = fields.Char('id')
    name = fields.Char('Nombre', tracking=True)
    fotografia = fields.Image('Fotografia', tracking=True)
    fotografia_mini = fields.Image("Miniatura", related="fotografia", max_width=128, max_height=64, tracking=True)
    ciudad_id = fields.Many2one('res.city', string='Ciudad', tracking=True)
    entrenamiento_ids = fields.One2many('stmg_jamboree.entrenamiento', 'sede_id', string='Entrenamientos', tracking=True)
    
    entrenamientos_30dias = fields.Integer(string='Entrenamientos para 30 días', compute='_contador')
    
    

    def _contador(self):
        
        for record in self:
            contador = 0
            for entrenamiento in record.entrenamiento_ids:
                resta = entrenamiento.turno - datetime.today()
                dias = resta.days
                if(dias < 31 and dias >= 0):
                    contador += 1
                    
            record.entrenamientos_30dias = contador

    @api.model_create_multi
    def create(self, values_list):
        records = super(Sede, self).create(values_list)
        
        for record in records:
            record.cod = f'SEDE_{record.id}'
        
        return records
