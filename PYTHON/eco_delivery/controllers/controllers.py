# -*- coding: utf-8 -*-
from odoo import http

# class EcoDelivery(http.Controller):
#     @http.route('/eco_delivery/eco_delivery/', auth='public')
#     def index(self, **kw):
#         return "Hello, world"

#     @http.route('/eco_delivery/eco_delivery/objects/', auth='public')
#     def list(self, **kw):
#         return http.request.render('eco_delivery.listing', {
#             'root': '/eco_delivery/eco_delivery',
#             'objects': http.request.env['eco_delivery.eco_delivery'].search([]),
#         })

#     @http.route('/eco_delivery/eco_delivery/objects/<model("eco_delivery.eco_delivery"):obj>/', auth='public')
#     def object(self, obj, **kw):
#         return http.request.render('eco_delivery.object', {
#             'object': obj
#         })