# -*- coding: utf-8 -*-

from odoo import models, fields, api
from datetime import datetime

class Jugador(models.Model):
    _name = 'stmg_jamboree.jugador'
    _description = 'Jugador'
    _inherit = ['mail.thread', 'mail.activity.mixin']
    
    name = fields.Char('id', readonly=True)
    nombre = fields.Char('Nombre', tracking=True)
    apellidos = fields.Char('Apellidos', tracking=True)
    nacimiento = fields.Date('Nacimiento', tracking=True)
    equipo = fields.Char('Equipo', tracking=True)
    objetivos = fields.Char('Objetivos', tracking=True)
    genero = fields.Char('Genero', tracking=True)
    posicion = fields.Char('Posicion', tracking=True)
    lateralidad = fields.Selection([
        ('derecha', 'Derecha'),
        ('izquierda', 'Izquierda')
    ], string='Lateralidad', tracking=True)
    categoria = fields.Selection([
        ('aficionado', 'Aficionado'),
        ('juvenil', 'Juvenil'),
        ('cadete', 'Cadete'),
        ('infantil', 'Infantil'),
        ('alevin', 'Alevin'),
        ('benjamin', 'Benjamin'),
        ('prebenjamin', 'Prebenjamin'),
        ('debutante', 'Debutante'),
        ('chupetin', 'Chupetin'),
    ], string='Categoría', compute='_categoria_por_edad', readonly=True, store=True, tracking=True)
    dni = fields.Char('DNI', tracking=True)
    bono = fields.Char('Bono', tracking=True)
    sesiones = fields.Integer('Sesiones', tracking=True)
    fotografia = fields.Image('Fotografia', tracking=True)
    tutor_ids = fields.Many2many('stmg_jamboree.tutor', string='Tutor',relation='stmg_jamboree_tutor_jugador_rel', tracking=True)
    entrenamiento_ids = fields.Many2many('stmg_jamboree.entrenamiento', string='Entrenamientos',relation='stmg_jamboree_entrenamiento_jugador_rel', tracking=True)
    
    @api.depends('nacimiento')
    def _categoria_por_edad(self):
        for rec in self:
            if rec.nacimiento:
                año_nacimiento = rec.nacimiento.year  
                año_actual = datetime.today().year  
                edad = año_actual - año_nacimiento 

                if edad >= 19:
                    rec.categoria = "aficionado"
                elif edad >= 16:
                    rec.categoria = "juvenil"
                elif edad >= 14:
                    rec.categoria = "cadete"
                elif edad >= 12:
                    rec.categoria = "infantil"
                elif edad >= 10:
                    rec.categoria = "alevin"
                elif edad >= 8:
                    rec.categoria = "benjamin"
                elif edad >= 6:
                    rec.categoria = "prebenjamin"
                elif edad >= 4:
                    rec.categoria = "debutante"

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
    
    @api.model_create_multi
    def create(self, values_list):
        records = super(Jugador, self).create(values_list)
        
        for record in records:
            record.name = f'JUG_{record.id}'
        
        return records
