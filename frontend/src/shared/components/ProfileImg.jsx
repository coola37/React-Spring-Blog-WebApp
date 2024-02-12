import defaultProfileImg from "@/assets/profile.png";
export function ProfileImg({width, tempImage, image}){

    const profileImage = image ? `/assets/profile/${image}` : defaultProfileImg; 

    return(
        <img
        src={tempImage || profileImage}
        width={width}
        height={width}
        className="rounded-circle shadow-sm"
        onError={({target}) => {
          target.src = defaultProfileImg
        }}
      ></img>
    )
}