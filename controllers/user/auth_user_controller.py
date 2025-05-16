from abc import ABC, abstractmethod

class AuthUserController(ABC):
    @abstractmethod
    def auth_user(self, userInfo):
        """
        Método abstracto para autenticar un usuario por su ID.
        
        :param db: Sesión de base de datos.
        :param user_id: ID del usuario a autenticar.
        :return: Usuario autenticado o None si no existe.
        """
        pass