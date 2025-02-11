import unittest
import requests

BASE_URL = "http://localhost:8080/api"


class TestClienteEndpoints(unittest.TestCase):

    def test_obter_todos_clientes(self):
        response = requests.get(f"{BASE_URL}/cliente")
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json(), list)

    def test_salvar_cliente(self):
        novo_cliente = {
            "nome": "Cliente Teste",
            "cpf": "123.456.789-00",
            "email": "cliente@teste.com",
            "telefone": "11999999999",
            "dataNascimento": "1990-01-01",
            "sexo": "M",
            "apelido": "Teste",
        }
        response = requests.post(f"{BASE_URL}/cliente", json=novo_cliente)
        self.assertEqual(response.status_code, 201)
        self.assertIn("nome", response.json())

    def test_atualizar_cliente(self):
        cliente_atualizado = {
            "idCliente": 1,
            "nome": "Cliente Atualizado",
            "cpf": "123.456.789-00",
            "email": "cliente@teste.com",
            "telefone": "11999999999",
            "dataNascimento": "1990-01-01",
            "sexo": "M",
            "apelido": "Teste",
        }
        response = requests.put(f"{BASE_URL}/cliente", json=cliente_atualizado)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json()["nome"], "Cliente Atualizado")

    def test_deletar_cliente(self):
        cliente_para_deletar = {
            "idCliente": 1,
            "nome": "Cliente Teste",
            "cpf": "123.456.789-00",
            "email": "cliente@teste.com",
            "telefone": "11999999999",
            "dataNascimento": "1990-01-01",
            "sexo": "M",
            "apelido": "Teste",
        }
        response = requests.delete(f"{BASE_URL}/cliente", json=cliente_para_deletar)
        self.assertEqual(response.status_code, 200)

def suite():
    suite = unittest.TestSuite()
    suite.addTest(TestClienteEndpoints('test_obter_todos_clientes'))
    suite.addTest(TestClienteEndpoints('test_salvar_cliente'))
    suite.addTest(TestClienteEndpoints('test_atualizar_cliente'))
    suite.addTest(TestClienteEndpoints('test_deletar_cliente'))
    return suite

if __name__ == "__main__":
    runner = unittest.TextTestRunner()
    runner.run(suite())
