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
    'depends': ['base','l10n_es_toponyms','calendar', 'web', 'portal', 'web_company_color', 'mail'],

    # always loaded
    'data': [
        'security/groups.xml',
        'security/rules.xml',
        'security/ir.model.access.csv',
        'data/cron_jobs.xml',
        'views/interno/entrenador.xml',
        'views/interno/entrenamiento.xml',
        'views/interno/jugador.xml',
        'views/interno/sede.xml',
        'views/interno/tutor.xml',
        'views/interno/menus.xml',
        'views/interno/sincroContacto.xml',
        'views/portal/entrenamiento.xml',
        'views/portal/menu.xml',
    ],
    # only loaded in demonstration mode
    'demo': [
        'demo/demo.xml',
    ],

    'assets': {
        'web.assets_backend': [
            'stmg_jamboree/static/src/css/emoji.css',
        ],
    },

    "post_init_hook": "estilo_company",
    
    'license': 'LGPL-3',
    
}

