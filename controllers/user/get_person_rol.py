from abc import ABC, abstractmethod

class GetPersonRol(ABC):
    @abstractmethod
    def get_person_rol(self, db, rol_name: str):
        """
        Método abstracto para obtener un rol de una persona.
        
        :param db: Sesión de base de datos.
        :param rol_name: Nombre del rol a buscar.
        :return: Rol encontrado o creado.
        """
        pass
def getGetPersonRol():
    return GetPersonRol()