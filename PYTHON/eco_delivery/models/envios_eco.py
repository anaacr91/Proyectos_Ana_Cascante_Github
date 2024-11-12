from odoo import fields, models, api
from odoo.exceptions import ValidationError


class EnviosEco(models.Model):
    _name = 'eco_delivery.envios_eco'
    _description = 'Los envios más ecologicos'
    _rec_name = 'direccion_entrega'


    pedido = fields.Many2one(comodel_name='sale.order', string='Pedidos del envío', required=True)
    direccion_entrega = fields.Char('Dirección', required=True)
    fecha_solicitud = fields.Date(string='fecha solicitud', required=True)
    tipo_transporte = fields.Many2one('eco_delivery.transporte_eco', string='transporte del envío')
    fecha_entrega_prevista = fields.Date(string='fecha entrega prevista', required=True)
    estado = fields.Selection([
        ('pendiente', 'Pendiente de Envío'),
        ('reparto', 'En Reparto'),
        ('entregado', 'Entregado'),
        ('no_entregado', 'No entregado')
    ], string="Estado",
        default='pendiente',
        help='estado del envio')
    fecha_entrega = fields.Date(string='fecha entrega')

    @api.constrains('fecha_solicitud', 'fecha_entrega')
    def _validar_fecha_solicitud_no_superior_fecha_entrega(self):
        for record in self:
            if record.fecha_solicitud > record.fecha_entrega:
                raise ValidationError(" la fecha de la solicitud no puede ser superior a la fecha de entrega prevista.")