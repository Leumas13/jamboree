from odoo import models, api, fields

class Libro(models.Model):
    _name = 'sge_libreria.libro'
    _description = 'Libro'
    
    name = fields.Char('name')
    precio = fields.Float('precio')
    ejemplares = fields.Integer('ejemplares')
    fecha_compra = fields.Date('fecha_compra')
    segmano = fields.Boolean('segmano')
    estado = fields.Selection([
        #(se va en vase de datos, lo ve el usuario)
        ('0', 'Nuevo'),
        ('1', 'Usado'),
        ('2', 'Reciclar'),
        #default es el estado por defecto
    ], string='estado' default='0')