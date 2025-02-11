# -*- coding: utf-8 -*-

from odoo import models, fields, api

class Jugador(models.Model):
    _name = 'stmg_jamboree.jugador'
    _description = 'Jugador'
    
    name = fields.Char('id', readonly=True)
    nombre = fields.Char('nombre')
    apellidos = fields.Char('apellidos')
    nacimiento = fields.Date('nacimiento')
    equipo = fields.Char('equipo')
    objetivos = fields.Char('objetivos')
    genero = fields.Char('genero')
    posicion = fields.Char('posicion')
    lateralidad = fields.Char('lateralidad')
    categoria = fields.Char('categoria')
    dni = fields.Char('dni')
    bono = fields.Char('bono')
    sesiones = fields.Integer('sesiones')