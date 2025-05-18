from controllers.user.remove_person import RemovePerson
from models.persistence import DataBaseSession
from fastapi  import Depends ,HTTPException, status
from models.entities import User
from sqlalchemy.orm import Session
db_session= DataBaseSession()
class RemovePersonImp(RemovePerson):
    def __init__ (self, db_session: Session):
        self.db:Session = db_session
    def remove_person(self, user_id: int):
        try:
                
            user = self.db.query(User).filter(User.id == user_id).first()
            if not user:
                return None
            self.db.delete(user)
            self.db.commit()
            self.db.refresh(user)
            return user
        except Exception as e:
            self.db.rollback()
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail="Error while removing person",
                headers={"X-Error": str(e)}
            )
def getRemovePersonControllerImp( db: Session = Depends(db_session.get_db)):
    return RemovePersonImp(db)