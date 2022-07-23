precision mediump float;

uniform sampler2D Mdiffuse;
uniform sampler2D Mspecular;
unirorm float Mshininess;

uniform vec3 Ldirection;
uniform vec3 Lambient;
uniform vec3 Ldiffuse;
uniform vec3 Lspecular;

uniform vec3 viewPos;

attribute vec3 FragPos;
attribute vec3 Normal;
attribute vec3 TexCoords;

void main(){
// ambient
    vec3 ambient = light.ambient * texture(material.diffuse, TexCoords).rgb;

    // diffuse
    vec3 norm = normalize(Normal);
    // vec3 lightDir = normalize(light.position - FragPos);
    vec3 lightDir = normalize(-light.direction);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = light.diffuse * diff * texture(material.diffuse, TexCoords).rgb;

    // specular
    vec3 viewDir = normalize(viewPos - FragPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    vec3 specular = light.specular * spec * texture(material.specular, TexCoords).rgb;

    vec3 result = ambient + diffuse + specular;
    FragColor = vec4(result, 1.0);
}

























