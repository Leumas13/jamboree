# -*- coding: utf-8 -*-

from odoo import models, fields, api

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
    categoria = fields.Char('Categoria')
    dni = fields.Char('DNI')
    bono = fields.Char('Bono')
    sesiones = fields.Integer('Sesiones')
    fotografia = fields.Binary('Fotografia')
    tutor_ids = fields.Many2many('stmg_jamboree.tutor', string='Tutor',relation='stmg_jamboree_tutor_jugador_rel')
    entrenamiento_ids = fields.Many2many('stmg_jamboree.entrenamiento', string='Entrenamientos',relation='stmg_jamboree_entrenamiento_jugador_rel')
    