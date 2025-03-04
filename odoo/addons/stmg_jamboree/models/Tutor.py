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

    @api.model_create_multi
    def create(self, values_list):
        ultimo_tutor = self.search([], order="id desc", limit=1)
        numero = 1

        if ultimo_tutor and ultimo_tutor.name:
            try:
                numero = int(ultimo_tutor.name[6:]) + 1
            except ValueError:
                numero = 1

        for values in values_list:
            values['name'] = f'TUTOR_{numero}'
            numero += 1

        records = super(Tutor, self).create(values_list)
        return records
