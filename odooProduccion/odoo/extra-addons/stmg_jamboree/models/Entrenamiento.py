from odoo import models, api, fields
from datetime import datetime, timedelta

class Entrenamiento(models.Model):
    _name = "stmg_jamboree.entrenamiento"
    _description = 'Entrenamiento'
    _inherit = ['mail.thread', 'mail.activity.mixin']
    
    name = fields.Char('id', readonly=True)
    tipo = fields.Selection([
        ('personal', 'Personal'),
        ('infantil', 'Infantil'),
        ('desarrollo', 'Desarrollo'),
    ], string='Tipo', required=True, tracking=True)
    turno = fields.Datetime('Turno', required=True, tracking=True)
    sede_id = fields.Many2one('stmg_jamboree.sede', string='Sede', required=True, tracking=True)
    
    sede_nombre = fields.Char(related='sede_id.name', string="Nombre de Sede", tracking=True)
    sede_foto_mini = fields.Image(related='sede_id.fotografia_mini', string="Miniatura de Sede")
    sede_foto = fields.Image(related='sede_id.fotografia', string="Foto de Sede")
    
    entrenador_ids = fields.Many2many(
                        'res.partner',
                        'res_partner_stmg_jamboree_entrenamiento_rel',
                        string='Entrenadores',
                        domain=[('is_entrenador', '=', True)],
                        tracking=True
                    )
    
    user_ids = fields.Many2many('res.users', compute='_compute_user_ids', store=True)
    jugador_ids = fields.Many2many('stmg_jamboree.jugador', string='Jugador', relation='stmg_jamboree_entrenamiento_jugador_rel', tracking=True)
    
    terminado = fields.Boolean(string='Entrenamiento terminado', compute='_comprobar_fecha')
    dias = fields.Integer('dias')
    
    calendar_event_id = fields.Many2one('calendar.event', string="Evento de calendario", ondelete='set null')
    
    # Campos para la ocupación
    capacidad = fields.Integer('Capacidad')    
    numero_jugadores = fields.Integer(
        string='Número de Jugadores',
        compute='_compute_numero_jugadores',
        store=True
    )

    # Colores y emojis para la ocupación
    color_ocupacion = fields.Char('color ocupacion', compute='_compute_color_ocupacion', store=False)
    color_fondo = fields.Char('color fondo', compute='_compute_color_ocupacion', store=False)
    emoji_ocupacion = fields.Char('emoji ocupacion', compute='_compute_color_ocupacion', store=False)

    # Cargar pdf con entrenamiento
    ficha_pdf = fields.Binary("Ficha PDF", attachment=True)
    ficha_pdf_filename = fields.Char("Nombre del archivo", tracking=True)

   

    @api.depends('entrenador_ids.user_id')
    def _compute_user_ids(self):
        for record in self:
            record.user_ids = record.entrenador_ids.mapped('user_id').filtered(lambda u: u)


    @api.depends('numero_jugadores', 'capacidad')
    def _compute_color_ocupacion(self):
        for rec in self:
            if rec.capacidad:
                ratio = rec.numero_jugadores / rec.capacidad
                if ratio >= 0.7:
                    rec.color_ocupacion = '#0e6251'
                    rec.color_fondo = '#d1f2eb'
                    rec.emoji_ocupacion = '😁'
                elif ratio >= 0.4:
                    rec.color_ocupacion = '#7e5109'
                    rec.color_fondo = '#fdebd0'
                    rec.emoji_ocupacion = '😒'
                else:
                    rec.color_ocupacion = '#78281f'
                    rec.color_fondo = '#fadbd8'
                    rec.emoji_ocupacion = '😠' 
            else:
                rec.color_ocupacion = '#78281f'
                rec.color_fondo = '#fadbd8'
                rec.emoji_ocupacion = '😠'

    @api.depends('jugador_ids')
    def _compute_numero_jugadores(self):
        for record in self:
            record.numero_jugadores = len(record.jugador_ids)
            
    def _comprobar_fecha(self):
        for record in self:
            resta = record.turno - datetime.today()
            dias = resta.days
            record.dias = dias
            record.terminado = dias < 0
    
   
    @api.model_create_multi
    def create(self, values_list):
        records = super(Entrenamiento, self).create(values_list)
        for record in records:
            sede_id = record.sede_id.id if record.sede_id else None
            if sede_id:
                record.name = f'SEDE_{sede_id}_ENT_{record.id}'
            record.crear_evento_calendario()
        return records

    def write(self, values):
        res = super(Entrenamiento, self).write(values)
        
        for record in self:
            record.crear_evento_calendario()
        return res

    def crear_evento_calendario(self):
        for record in self:
            calendar_event = record.calendar_event_id

            # Verificar si el evento todavía existe (evita MissingError)
            if calendar_event and not calendar_event.exists():
                record.calendar_event_id = False
                calendar_event = None

            if calendar_event:
                calendar_event.write({
                    'name': f"Entrenamiento {record.name}",
                    'start': record.turno,
                    'stop': record.turno + timedelta(hours=1),
                    'location': record.sede_nombre,
                })
            else:
                event_vals = {
                    'name': f"Entrenamiento {record.name}",
                    'start': record.turno,
                    'stop': record.turno + timedelta(hours=1),
                    'allday': False,
                    'location': record.sede_nombre,
                }
                event = self.env['calendar.event'].create(event_vals)
                record.calendar_event_id = event.id

