from sqlalchemy.orm import Session
from models.entities import User
from controllers.user import GetUserController
from fastapi import Depends
from models.persistence.DatabaseSession import DataBaseSession

db_session= DataBaseSession()
class GetUserImp(GetUserController):
    def __init__(self ,db: Session):
        self.db= db
        
    def get_user(self, user_id: int):
        user = self.db.query(User).filter(User.id == user_id).first()
        if not user:
            return None
        return None
    
    def get_all_users(self):
        users = self.db.query(User).all()
        if not users:
            return None
        return users
    
def getGetUserControllerImp( db: Session = Depends(db_session.get_db)):
    return GetUserImp(db)