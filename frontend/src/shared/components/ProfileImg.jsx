import defaultProfileImg from "@/assets/profile.png";
export function ProfileImg({width, tempImage}){
    return(
        <img
        src={tempImage || defaultProfileImg}
        width={width}
        height={width}
        className="rounded-circle shadow-sm"
      ></img>
    )
}