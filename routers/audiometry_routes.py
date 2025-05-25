from fastapi import APIRouter, Depends
from schemas.Audiometry import Audiometry as AudiometrySchema
from controllers.audiometry.add_audiometry import AddAudiometryController
from controllers.audiometry.imp.add_audiometry_imp import getAddAudiometryController
from controllers.audiometry.get_audiometry import GetAudiometryController
from schemas.UserSchema import AuthUser, UserCreate, UserOut
from controllers.user.imp.auth_user_controller_imp import  get_auth_controller,get_current_user
from controllers.audiometry.imp.get_audiometry_imp import GetAudiometryControllerImp,get_audiometry_imp_controller
# Definir el router
router = APIRouter(prefix="/audiometries", tags=["Audiometries"])


@router.post("/", response_model=AudiometrySchema)
def create_audiometry(
    audiometry_data: AudiometrySchema,
    add_audiometry_controller: AddAudiometryController = Depends(getAddAudiometryController)):
    
    return add_audiometry_controller.add_audiometry(audiometry_data)
    
@router.get("/" )
def get_my_audiometries(
    get_audiometry_controller: GetAudiometryController = Depends(get_audiometry_imp_controller),
    current_user: UserOut = Depends(get_current_user)):
    
    return get_audiometry_controller.getAudiometryByUserId(current_user.id)