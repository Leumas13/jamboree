from odoo import api

def estilo_company(env):
    company = env['res.company'].browse(1)

    if company.exists():
        company.write({
            'name': 'Jamboree',
            'email': 'info@jamboreesport.es',
            'phone': '+34 123 456 789',
            'color_navbar_bg': '#6cbe8d',
            'color_navbar_bg_hover': '#1a4a30',
            'color_navbar_text': '#ffffff',
            'color_button_bg': '#1a4a30',
            'color_button_bg_hover': '#6cbe8d',
            'color_button_text': '#ffffff',
            'color_link_text_hover': '#6cbe8d',
            'color_link_text': '#1a4a30',
        })
        company.scss_create_or_update_attachment()
