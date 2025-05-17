from auth_user_controller import AuthUserController
from sqlalchemy.orm import Session
from ....schemas import AuthUser
from models.entities import User
from hashlib import sha256
class AuthUserControllerImp(AuthUserController):
    def __init__(self, db: Session):
        self.db= db

    def auth_user(self,AuthUser: AuthUser):
        username = self.db.query(User).filter(User.username == AuthUser.username).first()
        password = sha256(AuthUser.password.encode()).hexdigest()
        if username:
            if username.password == password:
                return True
        else:
            return False
        
def getAuthUserController():
    return AuthUserControllerImp()
"""
    def logout(self):
        return self.auth_user_service.logout()

    def register(self, user_data):
        return self.auth_user_service.register(user_data)"""