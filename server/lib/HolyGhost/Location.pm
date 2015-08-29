package HolyGhost::Location;
use Mojo::Base 'Mojolicious::Controller';

sub get {
   my $self = shift;
   my $id = $self->param('id');

   my $location = $self->db->resultset('Location')->single({id => $id});
   if ($location) {
      my %cols = $location->get_columns;
      $self->render(json => \%cols);
   } else {
      $self->not_found;
   }
}

sub get_all {
   my $self = shift;
   my @locations;

   foreach my $row ($self->db->resultset('Location')->all) {
      my %cols = $row->get_columns;
      push @locations, \%cols;
   }

   $self->render(json => \@locations);
}

1;
