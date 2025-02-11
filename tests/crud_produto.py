import unittest
import requests

BASE_URL = "http://localhost:8080/api"


class TestProdutoEndpoints(unittest.TestCase):

    def test_obter_todos_produtos(self):
        response = requests.get(f"{BASE_URL}/produto")
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json(), list)

    def test_salvar_produto(self):
        novo_produto = {
            "nome": "Produto Teste",
            "descricao": "Descrição do Produto Teste",
            "preco": 99.99,
            "ativo": True,
        }
        response = requests.post(f"{BASE_URL}/produto", json=novo_produto)
        self.assertEqual(response.status_code, 200)
        self.assertIn("nome", response.json())

    def test_atualizar_produto(self):
        produto_atualizado = {
            "idProduto": 1,
            "nome": "Produto Atualizado",
            "descricao": "Descrição Atualizada",
            "preco": 199.99,
            "ativo": False,
        }
        response = requests.put(f"{BASE_URL}/produto", json=produto_atualizado)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json()["nome"], "Produto Atualizado")

    def test_deletar_produto(self):
        produto_para_deletar = {
            "idProduto": 1,
            "nome": "Produto Teste",
            "descricao": "Descrição do Produto Teste",
            "preco": 99.99,
            "ativo": True,
        }
        response = requests.delete(f"{BASE_URL}/produto", json=produto_para_deletar)
        self.assertEqual(response.status_code, 200)


def suite():
    suite = unittest.TestSuite()
    suite.addTest(TestProdutoEndpoints("test_obter_todos_produtos"))
    suite.addTest(TestProdutoEndpoints("test_salvar_produto"))
    suite.addTest(TestProdutoEndpoints("test_atualizar_produto"))
    suite.addTest(TestProdutoEndpoints("test_deletar_produto"))
    return suite


if __name__ == "__main__":
    runner = unittest.TextTestRunner()
    runner.run(suite())
