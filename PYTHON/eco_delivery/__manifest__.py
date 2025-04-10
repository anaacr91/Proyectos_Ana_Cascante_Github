# -*- coding: utf-8 -*-
{
    'name': "eco_delivery",

    'summary': """
        Delivery ecológico: Módulo de transportes para los envios de los quioscos a las personas y entre quioscos
    """,

    'description': """
        Módulo transportes
    """,

    'author': "Quioscos Ana Cascante",
    'website': "http://www.QuioscosAna.com",

    # Categories can be used to filter modules in modules listing
    # Check https://github.com/odoo/odoo/blob/12.0/odoo/addons/base/data/ir_module_category_data.xml
    # for the full list
    'category': 'Transportes',
    'version': '0.1',

    # any module necessary for this one to work correctly
    'depends': ['base'],

    # always loaded
    'data': [
        # 'security/ir.model.access.csv',
        'views/views.xml',
        'views/templates.xml',
        'views/envios_eco_view.xml',
        'views/transportes_eco_view.xml',
        'security/security.xml',
        'security/ir.model.access.csv',
    ],
    # only loaded in demonstration mode
    'demo': [
        'demo/demo.xml',
    ],

    'installable': True,
    'application': True,
}