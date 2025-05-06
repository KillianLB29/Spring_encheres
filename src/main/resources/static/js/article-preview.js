const inputImage = document.getElementById("image");
const preview = document.getElementById("imagePreview");

inputImage.addEventListener("change", function () {
    const file = this.files[0];
    if (file && file.type.startsWith("image/")) {
        const reader = new FileReader();
        reader.onload = function (e) {
            preview.src = e.target.result;
            preview.style.display = "block";
        };
        reader.readAsDataURL(file);
    } else {
        preview.src = "#";
        preview.style.display = "none";
    }
});