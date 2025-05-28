from odoo import http
from datetime import date
from odoo.http import request
from odoo.addons.portal.controllers.portal import CustomerPortal

class PortalEntrenamientos(CustomerPortal):

    @http.route(['/my/entrenamientos'], type='http', auth="user", website=True)
    def portal_entrenamientos(self, **kwargs):
        entrenamientos = request.env['stmg_jamboree.entrenamiento'].sudo().search(
            [
                ('entrenador_ids.user_id', '=', request.env.user.id),
                ('turno', '>=', date.today())
            ],
            order='turno asc'
        )
        values = {
            'entrenamientos': entrenamientos,
        }
        return request.render("stmg_jamboree.stmg_portal_entrenamientos", values)
