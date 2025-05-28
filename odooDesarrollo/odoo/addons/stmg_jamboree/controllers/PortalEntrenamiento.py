from odoo import http, fields
from odoo.http import request
from odoo.addons.portal.controllers.portal import CustomerPortal
from odoo.tools import format_datetime
from odoo.fields import Date
from datetime import datetime, timedelta

class PortalEntrenamientos(CustomerPortal):

    @http.route(['/my/entrenamientos'], type='http', auth="user", website=True)
    def portal_entrenamientos(self, **kwargs):
        tz = request.session.tz or request.env.user.tz or 'UTC'

        entrenamientos = request.env['stmg_jamboree.entrenamiento'].sudo().search([
            ('entrenador_ids.user_id', '=', request.env.user.id),
            ('turno', '>=', Date.today())
        ], order='turno asc')

        ahora = fields.Datetime.now()

        entrenamientos_data = [{
            'ent': ent,
            'turno_formateado': format_datetime(request.env, ent.turno, tz=tz, dt_format='dd/MM/yyyy HH:mm'),
            'mostrar_ficha': ent.ficha_pdf and (ent.turno - ahora) <= timedelta(hours=2)
        } for ent in entrenamientos]

        return request.render("stmg_jamboree.stmg_portal_entrenamientos", {
            'entrenamientos': entrenamientos_data,
        })
