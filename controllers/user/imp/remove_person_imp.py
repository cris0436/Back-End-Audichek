from controllers.user.remove_person import RemovePerson
from models.persistence import DataBaseSession
from fastapi  import Depends
from models.entities import User
from sqlalchemy.orm import Session
db_session= DataBaseSession()
class RemovePersonImp(RemovePerson):
    def __init__ (self, db_session: Session):
        self.db:Session = db_session
    def remove_person(self, user_id: int):
        user = self.db.query(User).filter(User.id == user_id).first()
        if not user:
            return None
        self.db.delete(user)
        self.db.commit()
        self.db.refresh(user)
        return user
def getRemovePersonControllerImp( db: Session = Depends(db_session.get_db)):
    return RemovePersonImp(db)