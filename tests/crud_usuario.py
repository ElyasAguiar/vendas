import unittest
import requests

BASE_URL = "http://localhost:8080/api"


class TestUsuarioEndpoints(unittest.TestCase):

    def test_obter_todos_usuarios(self):
        response = requests.get(f"{BASE_URL}/usuario")
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json(), list)

    def test_salvar_usuario(self):
        novo_usuario = {
            "userName": "testuser",
            "password": "testpass",
            "ativo": True,
            "idCliente": 1,
        }
        response = requests.post(f"{BASE_URL}/usuario", json=novo_usuario)
        self.assertEqual(response.status_code, 200)
        self.assertIn("userName", response.json())

    def test_atualizar_usuario(self):
        usuario_atualizado = {
            "idUsuario": 1,
            "userName": "updateduser",
            "password": "updatedpass",
            "ativo": False,
            "idCliente": 1,
        }
        response = requests.put(f"{BASE_URL}/usuario", json=usuario_atualizado)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json()["userName"], "updateduser")

    def test_deletar_usuario(self):
        usuario_para_deletar = {
            "idUsuario": 1,
            "userName": "testuser",
            "password": "testpass",
            "ativo": True,
            "idCliente": 1,
        }
        response = requests.delete(f"{BASE_URL}/usuario", json=usuario_para_deletar)
        self.assertEqual(response.status_code, 200)


def suite():
    suite = unittest.TestSuite()
    suite.addTest(TestUsuarioEndpoints("test_obter_todos_usuarios"))
    suite.addTest(TestUsuarioEndpoints("test_salvar_usuario"))
    suite.addTest(TestUsuarioEndpoints("test_atualizar_usuario"))
    suite.addTest(TestUsuarioEndpoints("test_deletar_usuario"))
    return suite


if __name__ == "__main__":
    runner = unittest.TextTestRunner()
    runner.run(suite())
