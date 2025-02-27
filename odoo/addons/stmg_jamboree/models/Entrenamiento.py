from odoo import models, api, fields
from datetime import datetime

class Entrenamiento(models.Model):
    _name = "stmg_jamboree.entrenamiento"
    _description = 'Entrenamiento'
    
    name = fields.Char('id', required=True)
    tipo = fields.Selection([
        ('personal', 'Personal'),
        ('infantil', 'Infantil'),
        ('desarrollo', 'Desarrollo'),
    ], string='Tipo')
    turno = fields.Datetime('Turno', index=True)
    
    sede_id = fields.Many2one('stmg_jamboree.sede', string='Sede')
    nombre_sede = fields.Char(related='sede_id.nombre', string="Nombre de Sede")
    
    entrenador_ids = fields.Many2many('stmg_jamboree.entrenador', string='Entrenador', relation='stmg_jamboree_entrenamiento_entrenador_rel')
    jugador_ids = fields.Many2many('stmg_jamboree.jugador', string='Jugador', relation='stmg_jamboree_entrenamiento_jugador_rel')

    lista_entrenador = fields.Text(string='Lista de Entrenadores', compute='_entrenadores_str')
    
    
    
    terminado = fields.Boolean(string='Entrenamiento terminado', compute='_comprobar_fecha')
    dias = fields.Integer('dias')
    
    
    @api.depends('turno')
    def _comprobar_fecha(self):
        for record in self:
            resta = record.turno - datetime.today()
            dias = resta.days
            record.dias = dias
            record.terminado = dias < 0
    
    
    
    @api.depends('entrenador_ids')
    def _entrenadores_str(self):
        for record in self:
            nombres_completos = ""
            primer_registro = False
            for entrenador in record.entrenador_ids:
                if(primer_registro):
                    nombres_completos += ", "
                    
                nombres_completos += f"{entrenador.nombre} {entrenador.apellidos}"
                primer_registro = True
                
            
            
            record.lista_entrenador = nombres_completos