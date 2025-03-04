from odoo import models, api, fields
from datetime import datetime

class Sede(models.Model):
    _name = 'stmg_jamboree.sede'
    _description = 'Sede'
    
    name = fields.Char('id')
    nombre = fields.Char('Nombre')
    fotografia = fields.Image('Fotografia')
    fotografia_mini = fields.Image("Miniatura", related="fotografia", max_width=128, max_height=64)
    ciudad_id = fields.Many2one('res.city', string='Ciudad')
    entrenamiento_ids = fields.One2many('stmg_jamboree.entrenamiento', 'sede_id', string='Entrenamientos')
    
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
        ultima_sede = self.search([], order="id desc", limit=1)
        numero = 1

        if ultima_sede and ultima_sede.name:
            try:
                numero = int(ultima_sede.name[5:]) + 1
            except ValueError:
                numero = 1

        for values in values_list:
            values['name'] = f'SEDE_{numero}'
            numero += 1

        records = super(Sede, self).create(values_list)
        return records
