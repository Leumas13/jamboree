# -*- coding: utf-8 -*-

from odoo import models, fields, api
from datetime import datetime

class Jugador(models.Model):
    _name = 'stmg_jamboree.jugador'
    _description = 'Jugador'
    
    name = fields.Char('id', readonly=True)
    nombre = fields.Char('Nombre')
    apellidos = fields.Char('Apellidos')
    nacimiento = fields.Date('Nacimiento')
    equipo = fields.Char('Equipo')
    objetivos = fields.Char('Objetivos')
    genero = fields.Char('Genero')
    posicion = fields.Char('Posicion')
    lateralidad = fields.Selection([
        ('derecha', 'Derecha'),
        ('izquierda', 'Izquierda')
    ], string='Lateralidad')
    categoria = fields.Selection([
        ('juvenil', 'Juvenil'),
        ('cadete', 'Cadete'),
        ('infantil', 'Infantil'),
        ('alevin', 'Alevin'),
        ('benjamin', 'Benjamin'),
        ('prebenjamin', 'Prebenjamin'),
        ('chupetin', 'Chupetin'),
    ], string='Categoría', compute='_categoria_por_edad', readonly=True, store=True)
    dni = fields.Char('DNI')
    bono = fields.Char('Bono')
    sesiones = fields.Integer('Sesiones')
    fotografia = fields.Image('Fotografia')
    tutor_ids = fields.Many2many('stmg_jamboree.tutor', string='Tutor',relation='stmg_jamboree_tutor_jugador_rel')
    entrenamiento_ids = fields.Many2many('stmg_jamboree.entrenamiento', string='Entrenamientos',relation='stmg_jamboree_entrenamiento_jugador_rel')
    
    @api.depends('nacimiento')
    def _categoria_por_edad(self):
        for rec in self:
            if rec.nacimiento:
                año_nacimiento = rec.nacimiento.year  
                año_actual = datetime.today().year  
                edad = año_actual - año_nacimiento 

                if edad >= 19:
                    rec.categoria = "juvenil"
                elif edad >= 16:
                    rec.categoria = "cadete"
                elif edad >= 13:
                    rec.categoria = "infantil"
                elif edad >= 10:
                    rec.categoria = "alevin"
                elif edad >= 7:
                    rec.categoria = "benjamin"
                elif edad >= 5:
                    rec.categoria = "prebenjamin"
                else:
                    rec.categoria = "chupetin"

    # calcular categoria con una tarea programada cada vez que se cambia de año
    @api.model
    def _actualizar_categoria(self):
        jugadores = self.search([])        
        for jugador in jugadores:
            jugador._categoria_por_edad()

    
    # calcular categoria si se actualiza el jugador
    def write(self, values):
        if 'nacimiento' in values:
            self._categoria_por_edad()

        result = super(Jugador, self).write(values)
        return result
    
    @api.model
    def create(self, values):
        ultimo_jugador = self.search([], order="id desc", limit=1)
        numero = 1

        if ultimo_jugador:
            numero = int(ultimo_jugador.name[4:]) + 1

        values['name'] = f'JUG_{numero}'

        record = super(Jugador, self).create(values)
        return record