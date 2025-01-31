from odoo import models, fields, api

# class nombreDeClase(Herencia):
class Categoria(models.Model):
    # Los atributos seran campos de la tabla
    # al empezar por _ son privados
    
    #atributos privados que usa odoo
    #_name se usa para vincular con su tabla, nombre tecnico, stmg_jamboree.entrenador
    _name = 'sge_libreria.categoria'
    #_description descripcion literal que puede ver el usuario
    _description = 'Categoria'

    #atributos propios del objeto
    #atributo name va a columna, siempre se usa un atributo name y el relleno es la etiqueta
    #required es obligatorio y help es un tooltip
    name = fields.Char('Categoria', required = True, help="Introduce nombre de categoria")
    description = fields.Char('Descripcion', help="Introduce una descripcion")
    