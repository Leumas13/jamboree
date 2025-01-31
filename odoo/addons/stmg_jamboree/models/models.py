# -*- coding: utf-8 -*-

# from odoo import models, fields, api


# class stmg_jamboree(models.Model):
#     _name = 'stmg_jamboree.stmg_jamboree'
#     _description = 'stmg_jamboree.stmg_jamboree'

#     name = fields.Char()
#     value = fields.Integer()
#     value2 = fields.Float(compute="_value_pc", store=True)
#     description = fields.Text()
#
#     @api.depends('value')
#     def _value_pc(self):
#         for record in self:
#             record.value2 = float(record.value) / 100

