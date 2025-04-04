from odoo import models, api, fields
from datetime import datetime

class Entrenamiento(models.Model):
    _name = "stmg_jamboree.entrenamiento"
    _description = 'Entrenamiento'
    
    name = fields.Char('id', readonly=True)
    tipo = fields.Selection([
        ('personal', 'Personal'),
        ('infantil', 'Infantil'),
        ('desarrollo', 'Desarrollo'),
    ], string='Tipo', required=True)
    turno = fields.Datetime('Turno', required=True)
    
    sede_id = fields.Many2one('stmg_jamboree.sede', string='Sede', required=True)
    
    sede_nombre = fields.Char(related='sede_id.nombre', string="Nombre de Sede")
    sede_foto_mini = fields.Image(related='sede_id.fotografia_mini', string="Miniatura de Sede")
    sede_foto = fields.Image(related='sede_id.fotografia', string="Foto de Sede")
    
    entrenador_ids = fields.Many2many('stmg_jamboree.entrenador', string='Entrenador', relation='stmg_jamboree_entrenamiento_entrenador_rel')
    jugador_ids = fields.Many2many('stmg_jamboree.jugador', string='Jugador', relation='stmg_jamboree_entrenamiento_jugador_rel')

    lista_entrenador = fields.Text(string='Lista de Entrenadores', compute='_entrenadores_str')
    
    terminado = fields.Boolean(string='Entrenamiento terminado', compute='_comprobar_fecha')
    dias = fields.Integer('dias')
    
    
    
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

    @api.model_create_multi
    def create(self, values_list):
        for values in values_list:
            sede_id = values.get('sede_id')

            if sede_id:
                sede = self.env['stmg_jamboree.sede'].browse(sede_id)
                sede_nombre = sede.name

                ultimo_entrenamiento = self.search([('sede_id', '=', sede_id)], order="id desc", limit=1)
                numero = 1

                if ultimo_entrenamiento:
                    cabecera = len(sede_nombre) + 5 
                    try:
                        numero = int(ultimo_entrenamiento.name[cabecera:]) + 1
                    except ValueError:
                        numero = 1  

                values['name'] = f'{sede_nombre}_ENT_{numero}'

        records = super(Entrenamiento, self).create(values_list)
        return records
