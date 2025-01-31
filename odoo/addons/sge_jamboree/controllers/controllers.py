# -*- coding: utf-8 -*-
# from odoo import http


# class SgeJamboree(http.Controller):
#     @http.route('/sge_jamboree/sge_jamboree', auth='public')
#     def index(self, **kw):
#         return "Hello, world"

#     @http.route('/sge_jamboree/sge_jamboree/objects', auth='public')
#     def list(self, **kw):
#         return http.request.render('sge_jamboree.listing', {
#             'root': '/sge_jamboree/sge_jamboree',
#             'objects': http.request.env['sge_jamboree.sge_jamboree'].search([]),
#         })

#     @http.route('/sge_jamboree/sge_jamboree/objects/<model("sge_jamboree.sge_jamboree"):obj>', auth='public')
#     def object(self, obj, **kw):
#         return http.request.render('sge_jamboree.object', {
#             'object': obj
#         })

