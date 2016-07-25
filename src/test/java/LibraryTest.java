import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.StartContainerCmd;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class LibraryTest {

    @Test
    public void vnc() {
        DockerClientConfig config = DockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://192.168.99.100:2376")
                .withDockerTlsVerify(true)
                .withDockerCertPath("/Users/alex/.docker/machine/machines/dev")
                .build();

        final DockerClient client = DockerClientBuilder.getInstance(config).build();
        final CreateContainerCmd containerCmd = client.createContainerCmd("richnorth/vnc-recorder:latest");

        Volume volume = new Volume("/recorder");
        containerCmd.withBinds(new Bind("conf",volume));
        containerCmd.withCmd("-P", "/recorder/password", "vnchost");
        final CreateContainerResponse exec = containerCmd.exec();

        final StartContainerCmd startContainerCmd = client.startContainerCmd(exec.getId());
        startContainerCmd.exec();

    }

}
