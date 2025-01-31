# -*- coding: utf-8 -*-
# from odoo import http


# class StmgJamboree(http.Controller):
#     @http.route('/stmg_jamboree/stmg_jamboree', auth='public')
#     def index(self, **kw):
#         return "Hello, world"

#     @http.route('/stmg_jamboree/stmg_jamboree/objects', auth='public')
#     def list(self, **kw):
#         return http.request.render('stmg_jamboree.listing', {
#             'root': '/stmg_jamboree/stmg_jamboree',
#             'objects': http.request.env['stmg_jamboree.stmg_jamboree'].search([]),
#         })

#     @http.route('/stmg_jamboree/stmg_jamboree/objects/<model("stmg_jamboree.stmg_jamboree"):obj>', auth='public')
#     def object(self, obj, **kw):
#         return http.request.render('stmg_jamboree.object', {
#             'object': obj
#         })

