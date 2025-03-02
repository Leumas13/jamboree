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

    @api.model
    def create(self, values):
        ultimo_entrenador = self.search([], order="id desc", limit=1)
        numero = 1

        if ultimo_entrenador:
            numero = int(ultimo_entrenador.name[4:]) + 1

        values['name'] = f'ENT_{numero}'

        record = super(Entrenador, self).create(values)
        return record