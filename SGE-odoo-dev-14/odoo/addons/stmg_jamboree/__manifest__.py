# -*- coding: utf-8 -*-
{
    'name': "stmg_jamboree",

    'summary': "Escuela de futbol",

    'description': """
Long description of module's purpose
    """,

    'author': "My Company",
    'website': "https://www.yourcompany.com",
    'icon':'/stmg_jamboree/static/imgs/icono64.png',

    # Categories can be used to filter modules in modules listing
    # Check https://github.com/odoo/odoo/blob/15.0/odoo/addons/base/data/ir_module_category_data.xml
    # for the full list
    'category': 'Uncategorized',
    'version': '1.0',

    # any module necessary for this one to work correctly
    'depends': ['base','l10n_es_toponyms'],

    # always loaded
    'data': [
        'security/ir.model.access.csv',
        'views/entrenador.xml',
        'views/entrenamiento.xml',
        'views/jugador.xml',
        'views/sede.xml',
        'views/tutor.xml',
        'views/menus.xml',
    ],
    # only loaded in demonstration mode
    'demo': [
        'demo/demo.xml',
    ],
    
    'license': 'LGPL-3',
    
}

