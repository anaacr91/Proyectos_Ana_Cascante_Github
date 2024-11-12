from odoo import fields, models, api


class TransporteEco(models.Model):
    _name = 'eco_delivery.transporte_eco'
    _description = 'transporte ecologico para delivery'
    _rec_name = 'transporte'

    transporte = fields.Char('Transporte', required=True)
    descripcion = fields.Char('Descripción', required=True)

    envios = fields.One2many('eco_delivery.envios_eco', 'tipo_transporte', string = 'envios del transporte', required=True)
    _sql_constraints = [('PK_TransporteEco', 'unique(transporte)', 'Ya existe un Transporte con este transporte'),]

