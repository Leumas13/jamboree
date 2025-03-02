from odoo import models, fields, api

class Tutor(models.Model):
    _name = 'stmg_jamboree.tutor'
    _description = 'Tutor'
    
    name = fields.Char('id')
    nombre = fields.Char('Nombre')
    apellidos = fields.Char('Apellidos')
    email = fields.Char('e-mail')
    telefono = fields.Integer('Telefono')
    dni = fields.Char('DNI')
    jugador_ids = fields.Many2many('stmg_jamboree.jugador', string='Jugador',relation='stmg_jamboree_tutor_jugador_rel')

    @api.model
    def create(self, values):
        ultimo_tutor = self.search([], order="id desc", limit=1)
        numero = 1

        if ultimo_tutor:
            numero = int(ultimo_tutor.name[6:]) + 1

        values['name'] = f'TUTOR_{numero}'

        record = super(Tutor, self).create(values)
        return record