import unittest
import requests

BASE_URL = "http://localhost:8080/api"


class TestPedidoEndpoints(unittest.TestCase):

    def test_obter_todos_pedidos(self):
        response = requests.get(f"{BASE_URL}/pedidos")
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json(), list)

    def test_salvar_pedido(self):
        novo_pedido = {
            "numeroPedido": "12345",
            "valorTotal": 100.0,
            "dataHora": "2023-10-01T12:00:00Z",
            "ativo": True,
            "observacao": "Pedido de teste",
            "idFormaPagamento": 1,
            "idCliente": 1,
        }
        response = requests.post(f"{BASE_URL}/pedidos", json=novo_pedido)
        self.assertEqual(response.status_code, 200)
        self.assertIn("numeroPedido", response.json())

    def test_aplicar_desconto(self):
        response = requests.post(f"{BASE_URL}/pedidos/1/desconto")
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json(), float)


def suite():
    suite = unittest.TestSuite()
    suite.addTest(TestPedidoEndpoints("test_obter_todos_pedidos"))
    suite.addTest(TestPedidoEndpoints("test_salvar_pedido"))
    suite.addTest(TestPedidoEndpoints("test_aplicar_desconto"))
    return suite


if __name__ == "__main__":
    runner = unittest.TextTestRunner()
    runner.run(suite())
